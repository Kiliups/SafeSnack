// // Define the AuthorityEnum enum equivalent to Java's enum
// export enum AuthorityEnum {
//     ROLE_ADMIN = 'ROLE_ADMIN',
//     ROLE_USER = 'ROLE_USER',
//     // Add any other roles as needed
// }

export class Authority {
    id: number;
    authority: string;

    constructor(id: number, authority: string) {
        this.id = id;
        this.authority = authority;
    }

    // Equivalent of the getAuthority() method in Java
    getAuthority(): string {
        return this.authority;
    }
}