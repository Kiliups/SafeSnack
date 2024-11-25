import { writable } from 'svelte/store';
import type { UserMeta } from '../types/UserMeta';
import type {Authority} from "../types/Authority";

// Writable store to hold the current user data
export const currentUser = writable<UserMeta | null>(null);
export const currentUserRoles = writable<Authority[]>([]);


