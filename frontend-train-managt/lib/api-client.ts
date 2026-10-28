import type {
  ApiErrorBody,
  AuthResponse,
  LoginRequest,
  RegisterRequest,
  RegisterResponse,
  SpringPage,
  TrainQuery,
  TrainRequest,
  TrainResponse,
  TrainUpdateRequest,
} from "@/types/api";

const API_BASE_URL = (process.env.NEXT_PUBLIC_API_URL ?? "http://localhost:8080").replace(/\/$/, "");

export class ApiError extends Error {
  readonly status: number;
  readonly body?: ApiErrorBody;

  constructor(status: number, body?: ApiErrorBody) {
    super(body?.message ?? body?.error ?? `API request failed with status ${status}`);
    this.name = "ApiError";
    this.status = status;
    this.body = body;
  }
}

async function request<T>(path: string, init: RequestInit = {}): Promise<T> {
  const response = await fetch(`${API_BASE_URL}${path}`, {
    ...init,
    headers: {
      "Content-Type": "application/json",
      ...init.headers,
    },
  });

  if (response.status === 204) {
    return undefined as T;
  }

  const body = (await response.json().catch(() => undefined)) as T | ApiErrorBody | undefined;

  if (!response.ok) {
    throw new ApiError(response.status, body as ApiErrorBody | undefined);
  }

  return body as T;
}

function jsonRequest(method: string, body?: unknown, token?: string): RequestInit {
  return {
    method,
    body: body === undefined ? undefined : JSON.stringify(body),
    headers: token ? { Authorization: `Bearer ${token}` } : undefined,
  };
}

export const api = {
  register(payload: RegisterRequest) {
    return request<RegisterResponse>("/api/v1/auth/register", jsonRequest("POST", payload));
  },

  login(payload: LoginRequest) {
    return request<AuthResponse>("/api/v1/auth/login", jsonRequest("POST", payload));
  },

  createTrain(payload: TrainRequest, token?: string) {
    return request<TrainResponse>("/api/v1/add-train", jsonRequest("POST", payload, token));
  },

  listTrains(query: TrainQuery = {}, token?: string) {
    const params = new URLSearchParams();

    if (query.source) params.set("source", query.source);
    if (query.destination) params.set("destination", query.destination);
    if (query.type) params.set("type", query.type);
    if (query.page !== undefined) params.set("page", String(query.page));
    if (query.size !== undefined) params.set("size", String(query.size));
    if (query.sort) params.set("sort", query.sort);

    const queryString = params.toString();
    return request<SpringPage<TrainResponse>>(
      `/api/v1/trains${queryString ? `?${queryString}` : ""}`,
      jsonRequest("GET", undefined, token),
    );
  },

  getTrain(id: number, token?: string) {
    return request<TrainResponse>(`/api/v1/trains/${id}`, jsonRequest("GET", undefined, token));
  },

  updateTrain(id: number, payload: TrainUpdateRequest, token?: string) {
    return request<TrainResponse>(
      `/api/v1/trains/${id}`,
      jsonRequest("PUT", payload, token),
    );
  },

  deleteTrain(id: number, token?: string) {
    return request<void>(`/api/v1/trains/${id}`, jsonRequest("DELETE", undefined, token));
  },

  cancelTrain(id: number, token?: string) {
    return request<TrainResponse>(
      `/api/v1/trains/${id}/cancel`,
      jsonRequest("PATCH", undefined, token),
    );
  },

  setMaintenance(id: number, token?: string) {
    return request<TrainResponse>(
      `/api/v1/trains/${id}/maintenance`,
      jsonRequest("PATCH", undefined, token),
    );
  },
};
