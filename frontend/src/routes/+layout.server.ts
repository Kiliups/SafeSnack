import type { LayoutServerLoad } from '../../.svelte-kit/types/src/routes/$types';
import { isPathAllowed } from '../hooks.server';
import { redirect } from '@sveltejs/kit';

export const load: LayoutServerLoad = async ({ url, locals }) => {
	if (!locals.userContainer && !isPathAllowed(url.pathname)) {
		throw redirect(302, '/auth/login');
	}
	return {
		userContainer: locals.userContainer
	};
};
