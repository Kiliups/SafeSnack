import {derived, writable} from 'svelte/store';
import type {UserMeta} from '../types/UserMeta';
import type {Authority} from "../types/Authority";

// Writable store to hold the current user data
export const currentUser = writable<UserMeta | null>(null);
export const currentUserRoles = writable<Authority[]>([]);

// Derived store to track if the current user has 'Admin' role
export const isAdmin = derived(currentUserRoles, ($currentUserRoles) => {
    return $currentUserRoles.some(authority => authority.authority === 'ROLE_ADMIN');
});