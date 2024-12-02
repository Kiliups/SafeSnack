// src/+hooks.server.js
import { type Handle, redirect } from '@sveltejs/kit';
import { ResponseError, UserControllerApi } from './api_src';
import { apiConfig } from './utils/const';

const public_paths: string[] = ['/auth/login', '/auth/signup', '/auth/forgot'];

export function isPathAllowed(path: string): boolean {
	return public_paths.some(
		(allowedPath) => path === allowedPath || path.startsWith(allowedPath + '/')
	);
}

export const handle: Handle = async ({ event, resolve }) => {
	const sessionCookie = event.cookies.get('JSESSIONID');
	const url = new URL(event.request.url);

	console.log(
		'path:',
		url.pathname,
		'allowed:',
		isPathAllowed(url.pathname),
		'sessionCookie:',
		sessionCookie,
		'userContainer:',
		event.locals.userContainer
	);

	if (!sessionCookie && !isPathAllowed(url.pathname)) {
		throw redirect(302, '/auth/login');
	}

	if (sessionCookie && !event.locals.userContainer) {
		try {
			const api = new UserControllerApi(apiConfig(sessionCookie));
			const userContainer = await api.getUser();

			console.log('User data:', userContainer);
			event.locals.userContainer = userContainer;

			if (url.pathname == '/auth/signup' || url.pathname == '/auth/login') {
				throw redirect(302, '/');
			}
		} catch (error) {
			if (error instanceof ResponseError && error.response.status === 401) {
				console.log('Unauthorized:', error);
				event.cookies.delete('JSESSIONID', { path: '/' });
				throw redirect(302, '/auth/login');
			}
			console.error('Error fetching user:', error);
		}
	}

	return resolve(event);
};
