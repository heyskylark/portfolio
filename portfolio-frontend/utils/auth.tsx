import { NextPageContext } from 'next';
import Token from '../models/Token';
import Router from 'next/router';

export function login(token: Token): void {
  window.localStorage.setItem('token', `${token.token_type} ${token.access_token}`);
  if (token.refresh_token) {
    window.localStorage.setItem('refresh_token', token.refresh_token);
  }
}

export function auth(ctx: NextPageContext): boolean | undefined {
  const token = false;

  if (ctx.req && !token) {
    if (ctx.res) {
      ctx.res.writeHead(302, { Location: '/' });
      ctx.res.end();
      return;
    } else {
      Router.push('/');
    }
  }

  if (!token) {
    Router.push('/');
  }

  return token;
}
