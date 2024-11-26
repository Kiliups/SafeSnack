import { UserControllerApi } from '../api_src';
import { apiConfig } from '../utils/const';
import { authStore } from '../stores/auth';
import { goto } from '$app/navigation';

export async function setAuthStore() {
	const api = new UserControllerApi(apiConfig);
	const user = await api.getUser();
	console.log('User data:', user);
	if (user) {
		authStore.set(user);
	} else {
		console.error('Failed to fetch user data');
	}
}

export async function logout() {
	try {
		const response = await fetch('http://localhost:8080/logout', {
			method: 'POST',
			credentials: 'include'
		});

		if (response.ok) {
			authStore.set(null);
			await goto('/login');
		} else {
			console.error('Failed to logout');
		}
	} catch (error) {
		console.error('Error logging out:', error);
	}
}
