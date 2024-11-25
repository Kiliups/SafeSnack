<script lang="ts">
    import {currentUserRoles} from '../../stores/user.svelte';
    // Reactive variable to check if the user is an admin
    let isAdmin: boolean;


    console.log($currentUserRoles);

    // subscribe to the currentUserRoles store
    currentUserRoles.subscribe((value) => {
        console.log('subscribe triggered')
        console.log($currentUserRoles)
        // for each
        for (let authority of value) {
            if (authority.authority === 'ROLE_ADMIN') {
                isAdmin = true;
                break;
            }
        }
    });

    // for each
    for (let authority of $currentUserRoles) {
        if (authority.authority === 'ROLE_ADMIN') {
            isAdmin = true;
            break;
        }
    }
</script>

{#if isAdmin}
    <h1>Admin Dashboard</h1>
    <p>Welcome back!</p>
{:else}
    <h1>Sorry</h1>
    <p>You don't have access to the admin page.</p>
{/if}