import { type Actions, fail } from '@sveltejs/kit';
import {
	CITY,
	DESCRIPTION,
	EMAIL,
	NAME,
	PASSWORD,
	STREET,
	USERNAME,
	ZIP
} from '../../../utils/const';
import {
	type Restaurant,
	UserControllerApi,
	type UserMeta,
	type UserPrincipal
} from '../../../api_src';

export const actions: Actions = {
	signup: async ({ request }) => {
		console.log('signup');
		try {
			const data = await request.formData();
			const username = data.get(USERNAME)?.toString();
			const password = data.get(PASSWORD)?.toString();
			const name = data.get(NAME)?.toString();
			const street = data.get(STREET)?.toString();
			const city = data.get(CITY)?.toString();
			const zip = data.get(ZIP)?.toString();
			const description = data.get(DESCRIPTION)?.toString();
			const email = data.get(EMAIL)?.toString();

			if (!username || !password || !name || !email) {
				return fail(400, { message: 'All fields are required' });
			}

			if (!street && !city && !zip && !description) {
				const userMeta: UserMeta = {
					type: 'user_meta',
					name: name,
					email: email
				};
				const userPrincipal: UserPrincipal = {
					username: username,
					userMeta: userMeta
				};
				const api = new UserControllerApi();
				const response = await api.signUp({
					password: password,
					userPrincipal: userPrincipal
				});
				console.log(response);
			} else {
				console.log('restaurant');
				const restaurant: Restaurant = {
					type: 'restaurant',
					name: name,
					email: email,
					address: {
						street: street,
						city: city,
						zip: zip
					}
				};
				const userPrincipal: UserPrincipal = {
					username: username,
					userMeta: restaurant
				};
				const api = new UserControllerApi();
				console.log(userPrincipal);
				const response = await api.signUp({
					password: password,
					userPrincipal: userPrincipal
				});
				console.log(response);
			}

			return {
				type: 'success'
			};
		} catch (error) {
			console.error('Login error:', error);
			return fail(500, { message: 'An unexpected error occurred' });
		}
	}
};
