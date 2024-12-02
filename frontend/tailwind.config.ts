import type { Config } from 'tailwindcss';

export default {
	content: ['./src/**/*.{html,js,svelte,ts}'],

	theme: {
		extend: {
			colors: {
				ground: '#231420',
				primary: '#b8bdc4',
				accent: '#55736b',
				error: '#d04b51'
			}
		}
	},

	plugins: []
} satisfies Config;
