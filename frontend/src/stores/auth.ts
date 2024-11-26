import { derived, writable } from 'svelte/store';
import type { UserContainer } from '../api_src';

// Writable store to hold the current user data
export const authStore = writable<UserContainer | null>(null);

// Derived store to track if the current user has 'Admin' role
export const isAdmin = derived(authStore, (userContainer) => {
	return userContainer?.roles?.some((authority) => authority.authority === 'ROLE_ADMIN');
});
