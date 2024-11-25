import type {Config} from 'tailwindcss';

export default {
    content: ['./src/**/*.{html,js,svelte,ts}'],

    theme: {
        extend: {
            colors: {
                background: "#09192c",
                primary: "#a1a4a9",
                secondary: "#6c95a5",
                accent: "#b43347",
            }
        }
    },

    plugins: []
} satisfies Config;
