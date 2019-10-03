import Token from '../models/Token';

export function login(token: Token): void {
  window.localStorage.setItem('token', `${token.token_type} ${token.access_token}`);
  if (token.refresh_token) {
    window.localStorage.setItem('refresh_token', token.refresh_token);
  }
}
