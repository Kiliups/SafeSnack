<script lang="ts">
    import { goto } from '$app/navigation';

    let username = '';
    let password = '';
    let error = '';

    const handleSubmit = async (event: SubmitEvent) => {
        event.preventDefault();
        error = '';

        try {
            const response = await fetch('http://localhost:8080/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams({ username, password }),
                credentials: 'include', // Include cookies for session management
            });

            if (!response.ok) {
                if (response.status === 401) {
                    const message = await response.text(); // Read custom error message
                    error = message || 'Error occurred without message. Please try again.';
                } else {
                    error = 'An unexpected error occurred.';
                }
                return;
            }

            // Successful login
            error = "success!"
            await goto('/hello'); // Redirect to a secure page after login
        } catch (err) {
            error = 'An error occurred. Please try again.';
            console.error(err);
        }
    };
</script>

<style>
    .login-container {
        max-width: 400px;
        margin: auto;
        padding: 20px;
        border: 1px solid #ddd;
        border-radius: 8px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }

    .error-message {
        color: red;
        font-size: 0.9rem;
        margin-bottom: 10px;
    }

    input {
        width: 100%;
        padding: 10px;
        margin-bottom: 10px;
        border: 1px solid #ccc;
        border-radius: 4px;
    }

    button {
        width: 100%;
        padding: 10px;
        background-color: #007bff;
        color: #fff;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    button:hover {
        background-color: #0056b3;
    }
</style>

<div class="login-container">
    <form on:submit={handleSubmit}>
        <h1>Login</h1>

        {#if error}
            <div class="error-message">{error}</div>
        {/if}

        <input
                id="username"
                type="text"
                bind:value={username}
                placeholder="Enter your username"
                required
        />

        <input
                id="password"
                type="password"
                bind:value={password}
                placeholder="Enter your password"
                required
        />

        <button type="submit">Login</button>
    </form>
</div>