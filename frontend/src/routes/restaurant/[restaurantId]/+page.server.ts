import { RestaurantControllerApi } from '../../../api_src';
import { apiConfig } from '../../../utils/const';
import { error } from '@sveltejs/kit';
import type { PageServerLoadEvent } from './$types';

export async function load(event: PageServerLoadEvent) {
	const restaurantId = Number(event.params.restaurantId);
	let restaurant;
	const sessionCookie = event.cookies.get('JSESSIONID');
	console.log('restaurantId: ', restaurantId);
	try {
		const api = new RestaurantControllerApi(apiConfig(sessionCookie!));
		restaurant = await api.getRestaurantById({ id: restaurantId });
		console.log('restaurant: ', restaurant);
	} catch {
		error(404, 'Restaurant not found');
	}

	return {
		restaurant: restaurant
	};
}
