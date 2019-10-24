/* eslint-disable @typescript-eslint/camelcase */
import { NextPageContext } from 'next';
import Router from 'next/router';
import fetch from 'isomorphic-unfetch';
import nextCookie from 'next-cookies';
import cookie from 'js-cookie';
import { contextRedirect } from '../utils/redirect';
import AuthToken from '../models/authToken';
import NetworkError from '../models/NetworkError';

async function getTokenUsingCodeGrant(grantCode: string): Promise<AuthToken | NetworkError> {
  //TODO make url and redirectUrl variable
  const url = 'http://localhost:8080/oauth/token';
  const redirectUrl = 'http://localhost:3000/dashboard';
  const formData = new URLSearchParams();
  formData.append('grant_type', 'authorization_code');
  //TODO make client_id variable
  formData.append('client_id', 'testclient');
  formData.append('redirect_uri', redirectUrl);
  formData.append('code', grantCode);
  const request = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
    },
    body: formData,
  };
  const res = await fetch(url, request);
  const data = await res.json();

  try {
    if (res.ok) {
      return data;
    } else {
      return {
        status: res.status,
        errorDescription: data.error_description,
      };
    }
  } catch (err) {
    return {
      status: res.status,
      errorDescription: data.error_description,
    };
  }
}

export function isLoggedIn(ctx: NextPageContext): boolean {
  const { token } = nextCookie(ctx);
  return typeof token !== 'undefined';
}

export function login(token: AuthToken): void {
  const { access_token, jti } = token;
  cookie.set('token', access_token);
  cookie.set('tokenId', jti);
  Router.push('/dashboard');
}

export function logout(): void {
  cookie.remove('token');
  cookie.remove('tokenId');
  Router.push('/login');
}

// eslint-disable-next-line @typescript-eslint/explicit-function-return-type
// eslint-disable-next-line @typescript-eslint/no-explicit-any
function isAuthToken(obj: any): obj is AuthToken {
  return obj.access_token !== undefined;
}

export async function auth(
  ctx: NextPageContext,
  grantCode?: string,
): Promise<AuthToken | string | undefined> {
  let token: AuthToken | string | undefined;
  token = nextCookie(ctx).token;

  if (grantCode) {
    await getTokenUsingCodeGrant(grantCode).then(jwt => {
      if (isAuthToken(jwt)) {
        token = jwt;
      }
      // What to do during server error NetworkError, redirect?
    });
  }

  if (!token) {
    contextRedirect(ctx, '/login');
    return;
  }

  return token;
}
