<script lang="ts">
    import '../app.css';
    import Navbar from "../components/Navbar.svelte";
    import {goto} from "$app/navigation";
    import {authStore} from "../stores/auth";

    let {children} = $props();
    $effect(() => {
        // Check if the user is logged in, if not, redirect to the login page
        if (!$authStore && !window.location.pathname.includes("/login")) {
            goto('/login'); // Redirect to the login page
        }
    });
</script>

<Navbar/>
<section class="w-screen h-screen">
    {@render children()}
</section>

