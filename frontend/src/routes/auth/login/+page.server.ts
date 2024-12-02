import { type Actions, type Cookies, fail, redirect } from '@sveltejs/kit';

export const actions: Actions = {
	login: async ({ request, cookies }) => {
		try {
			const data = await request.formData();
			const username = data.get('username')?.toString();
			const password = data.get('password')?.toString();

			if (!username || !password) {
				return fail(400, { message: 'Username and password are required' });
			}

			const response = await fetch('http://localhost:8080/login', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded'
				},
				body: new URLSearchParams({ username, password })
			});

			if (!response.ok) {
				if (response.status === 401) {
					return fail(response.status, { message: 'Invalid username or password' });
				} else {
					return fail(response.status, { message: 'An unexpected error occurred' });
				}
			}

			const cookiesResult = setCookie(response, cookies);
			if (!cookiesResult) {
				return fail(500, { message: 'Failed to set session cookie' });
			}

			return {
				type: 'success'
			};
		} catch (error) {
			console.error('Login error:', error);
			return fail(500, { message: 'An unexpected error occurred' });
		}
	},
	logout: async ({ cookies, locals }) => {
		console.log('Logging out');
		const response = await fetch('http://localhost:8080/logout', {
			method: 'POST',
			headers: {
				Cookie: `JSESSIONID=${cookies.get('JSESSIONID')}`
			},
			credentials: 'include'
		});

		if (response.ok) {
			locals.userContainer = null;
			cookies.delete('JSESSIONID', { path: '/' });
			throw redirect(302, '/login');
		} else {
			console.error('Failed to logout');
		}
	}
};

function setCookie(response: Response, cookies: Cookies): boolean {
	const setCookieHeader = response.headers.get('set-cookie');
	if (!setCookieHeader) {
		return false;
	}

	// More robust cookie parsing
	const jsessionidMatch = setCookieHeader.match(/JSESSIONID=([^;]+)/);
	if (!jsessionidMatch) {
		return false;
	}

	cookies.set('JSESSIONID', jsessionidMatch[1], {
		path: '/'
	});
	return true;
}
