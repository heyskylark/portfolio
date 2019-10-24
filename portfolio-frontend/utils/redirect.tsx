import { NextPageContext } from 'next';
import Router from 'next/router';

export function contextRedirect(ctx: NextPageContext, redirect: string): void {
  if (ctx.res) {
    ctx.res.writeHead(302, { Location: redirect });
    ctx.res.end();
    return;
  } else {
    Router.push(redirect);
  }
}
