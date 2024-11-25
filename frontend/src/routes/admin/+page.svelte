<script lang="ts">
    import {isAdmin} from '../../stores/user.js';
</script>

{#if $isAdmin}
    <h1>Admin Dashboard</h1>
    <p>Welcome back!</p>
{:else}
    <h1>Sorry</h1>
    <p>You don't have access to the admin page.</p>
{/if}