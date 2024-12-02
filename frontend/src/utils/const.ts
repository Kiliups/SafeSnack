import { Configuration } from '../api_src';

export const apiConfig = (jsessionid: string) =>
	new Configuration({
		headers: {
			Cookie: `JSESSIONID=${jsessionid}`
		}
	});

export const USERNAME = 'username';
export const PASSWORD = 'password';
export const NAME = 'name';
export const STREET = 'street';
export const CITY = 'city';
export const ZIP = 'zip';
export const DESCRIPTION = 'description';
export const EMAIL = 'email';
