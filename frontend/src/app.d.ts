// See https://svelte.dev/docs/kit/types#app.d.ts
// for information about these interfaces
import type {UserContainer} from './api_src';

declare global {
	namespace App {
		// interface Error {}
		interface Locals {
			userContainer: UserContainer | null;
		}

		// interface PageData {}
		// interface PageState {}
		// interface Platform {}
	}
}

export {};
