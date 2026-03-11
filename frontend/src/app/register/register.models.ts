export interface RegisterRequest {
  email: string;
  name: string;
  password: string;
}

export interface User {
  id: string;
  email: string;
  name: string;
  active: boolean;
}
