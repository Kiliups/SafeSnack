<script lang="ts">
    import {currentUserRoles} from '../../stores/user.svelte';
    // Reactive variable to check if the user is an admin
    let isAdmin: boolean;
    $: isAdmin = $currentUserRoles.some(authority => authority.authority === 'ROLE_ADMIN');
</script>

{#if isAdmin}
    <h1>Admin Dashboard</h1>
    <p>Welcome back!</p>
{:else}
    <h1>Sorry</h1>
    <p>You don't have access to the admin page.</p>
{/if}