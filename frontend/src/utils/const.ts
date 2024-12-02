import { Configuration } from '../api_src';

export const apiConfig = (jsessionid: string) =>
	new Configuration({
		headers: {
			Cookie: `JSESSIONID=${jsessionid}`
		}
	});
