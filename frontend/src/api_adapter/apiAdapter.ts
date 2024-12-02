import { goto } from '$app/navigation';

export async function logout() {
	try {
		const response = await fetch('http://localhost:8080/logout', {
			method: 'POST',
			credentials: 'include'
		});

		if (response.ok) {
			await goto('/login');
		} else {
			console.error('Failed to logout');
		}
	} catch (error) {
		console.error('Error logging out:', error);
	}
}
