/* tslint:disable */
/* eslint-disable */
/**
 * 
 * @export
 * @interface Address
 */
export interface Address {
    /**
     * 
     * @type {number}
     * @memberof Address
     */
    id?: number;
    /**
     * 
     * @type {string}
     * @memberof Address
     */
    street?: string;
    /**
     * 
     * @type {string}
     * @memberof Address
     */
    city?: string;
    /**
     * 
     * @type {string}
     * @memberof Address
     */
    zip?: string;
}
/**
 * 
 * @export
 * @interface Allergen
 */
export interface Allergen {
    /**
     * 
     * @type {number}
     * @memberof Allergen
     */
    id?: number;
    /**
     * 
     * @type {string}
     * @memberof Allergen
     */
    name?: string;
    /**
     * 
     * @type {Allergy}
     * @memberof Allergen
     */
    allergy?: Allergy;
}
/**
 * 
 * @export
 * @interface Allergy
 */
export interface Allergy {
    /**
     * 
     * @type {number}
     * @memberof Allergy
     */
    id?: number;
    /**
     * 
     * @type {string}
     * @memberof Allergy
     */
    name?: string;
    /**
     * 
     * @type {Array<Allergen>}
     * @memberof Allergy
     */
    allergens?: Array<Allergen>;
}
/**
 * 
 * @export
 * @interface Authority
 */
export interface Authority {
    /**
     * 
     * @type {number}
     * @memberof Authority
     */
    id?: number;
    /**
     * 
     * @type {string}
     * @memberof Authority
     */
    authority?: string;
}
/**
 * 
 * @export
 * @interface Menu
 */
export interface Menu {
    /**
     * 
     * @type {number}
     * @memberof Menu
     */
    id?: number;
    /**
     * 
     * @type {string}
     * @memberof Menu
     */
    name?: string;
}
/**
 * 
 * @export
 * @interface PageRestaurant
 */
export interface PageRestaurant {
    /**
     * 
     * @type {number}
     * @memberof PageRestaurant
     */
    totalPages?: number;
    /**
     * 
     * @type {number}
     * @memberof PageRestaurant
     */
    totalElements?: number;
    /**
     * 
     * @type {number}
     * @memberof PageRestaurant
     */
    size?: number;
    /**
     * 
     * @type {Array<Restaurant>}
     * @memberof PageRestaurant
     */
    content?: Array<Restaurant>;
    /**
     * 
     * @type {number}
     * @memberof PageRestaurant
     */
    number?: number;
    /**
     * 
     * @type {Array<SortObject>}
     * @memberof PageRestaurant
     */
    sort?: Array<SortObject>;
    /**
     * 
     * @type {boolean}
     * @memberof PageRestaurant
     */
    first?: boolean;
    /**
     * 
     * @type {boolean}
     * @memberof PageRestaurant
     */
    last?: boolean;
    /**
     * 
     * @type {number}
     * @memberof PageRestaurant
     */
    numberOfElements?: number;
    /**
     * 
     * @type {PageableObject}
     * @memberof PageRestaurant
     */
    pageable?: PageableObject;
    /**
     * 
     * @type {boolean}
     * @memberof PageRestaurant
     */
    empty?: boolean;
}
/**
 * 
 * @export
 * @interface PageableObject
 */
export interface PageableObject {
    /**
     * 
     * @type {number}
     * @memberof PageableObject
     */
    offset?: number;
    /**
     * 
     * @type {Array<SortObject>}
     * @memberof PageableObject
     */
    sort?: Array<SortObject>;
    /**
     * 
     * @type {boolean}
     * @memberof PageableObject
     */
    unpaged?: boolean;
    /**
     * 
     * @type {boolean}
     * @memberof PageableObject
     */
    paged?: boolean;
    /**
     * 
     * @type {number}
     * @memberof PageableObject
     */
    pageNumber?: number;
    /**
     * 
     * @type {number}
     * @memberof PageableObject
     */
    pageSize?: number;
}
/**
 * 
 * @export
 * @interface PasswordChangeContainer
 */
export interface PasswordChangeContainer {
    /**
     * 
     * @type {string}
     * @memberof PasswordChangeContainer
     */
    oldPassword?: string;
    /**
     * 
     * @type {string}
     * @memberof PasswordChangeContainer
     */
    newPassword?: string;
}
/**
 * 
 * @export
 * @interface PasswordResetContainer
 */
export interface PasswordResetContainer {
    /**
     * 
     * @type {string}
     * @memberof PasswordResetContainer
     */
    token?: string;
    /**
     * 
     * @type {string}
     * @memberof PasswordResetContainer
     */
    newPassword?: string;
}
/**
 * 
 * @export
 * @interface Restaurant
 */
export interface Restaurant extends UserMetaBase {
    /**
     * 
     * @type {Address}
     * @memberof Restaurant
     */
    address?: Address;
    /**
     * 
     * @type {string}
     * @memberof Restaurant
     */
    description?: string;
    /**
     * 
     * @type {Menu}
     * @memberof Restaurant
     */
    menu?: Menu;
}
/**
 * 
 * @export
 * @interface RestaurantContainer
 */
export interface RestaurantContainer {
    /**
     * 
     * @type {Restaurant}
     * @memberof RestaurantContainer
     */
    restaurant?: Restaurant;
    /**
     * 
     * @type {Array<RestaurantRating>}
     * @memberof RestaurantContainer
     */
    restaurantRatings?: Array<RestaurantRating>;
}
/**
 * 
 * @export
 * @interface RestaurantRating
 */
export interface RestaurantRating {
    /**
     * 
     * @type {number}
     * @memberof RestaurantRating
     */
    id?: number;
    /**
     * 
     * @type {number}
     * @memberof RestaurantRating
     */
    rating?: number;
    /**
     * 
     * @type {string}
     * @memberof RestaurantRating
     */
    comment?: string;
    /**
     * 
     * @type {UserMeta}
     * @memberof RestaurantRating
     */
    user?: UserMeta;
    /**
     * 
     * @type {Restaurant}
     * @memberof RestaurantRating
     */
    restaurant?: Restaurant;
}
/**
 * 
 * @export
 * @interface SortObject
 */
export interface SortObject {
    /**
     * 
     * @type {string}
     * @memberof SortObject
     */
    direction?: string;
    /**
     * 
     * @type {string}
     * @memberof SortObject
     */
    nullHandling?: string;
    /**
     * 
     * @type {boolean}
     * @memberof SortObject
     */
    ascending?: boolean;
    /**
     * 
     * @type {string}
     * @memberof SortObject
     */
    property?: string;
    /**
     * 
     * @type {boolean}
     * @memberof SortObject
     */
    ignoreCase?: boolean;
}
/**
 * 
 * @export
 * @interface UserContainer
 */
export interface UserContainer {
    /**
     * 
     * @type {UserPrincipalUserMeta}
     * @memberof UserContainer
     */
    user?: UserPrincipalUserMeta;
    /**
     * 
     * @type {Array<Authority>}
     * @memberof UserContainer
     */
    roles?: Array<Authority>;
}
/**
 * 
 * @export
 * @interface UserMeta
 */
export interface UserMeta extends UserMetaBase {
    /**
     * 
     * @type {Array<Allergy>}
     * @memberof UserMeta
     */
    allergies?: Array<Allergy>;
}
/**
 * 
 * @export
 * @interface UserMetaBase
 */
export interface UserMetaBase {
    /**
     * 
     * @type {number}
     * @memberof UserMetaBase
     */
    id?: number;
    /**
     * 
     * @type {string}
     * @memberof UserMetaBase
     */
    name?: string;
    /**
     * 
     * @type {string}
     * @memberof UserMetaBase
     */
    type?: string;
    /**
     * 
     * @type {string}
     * @memberof UserMetaBase
     */
    email?: string;
}
/**
 * 
 * @export
 * @interface UserPrincipal
 */
export interface UserPrincipal {
    /**
     * 
     * @type {number}
     * @memberof UserPrincipal
     */
    id?: number;
    /**
     * 
     * @type {string}
     * @memberof UserPrincipal
     */
    username?: string;
    /**
     * 
     * @type {UserPrincipalUserMeta}
     * @memberof UserPrincipal
     */
    userMeta?: UserPrincipalUserMeta;
    /**
     * 
     * @type {Array<Authority>}
     * @memberof UserPrincipal
     */
    authorities?: Array<Authority>;
    /**
     * 
     * @type {boolean}
     * @memberof UserPrincipal
     */
    accountNonExpired?: boolean;
    /**
     * 
     * @type {boolean}
     * @memberof UserPrincipal
     */
    accountNonLocked?: boolean;
    /**
     * 
     * @type {boolean}
     * @memberof UserPrincipal
     */
    credentialsNonExpired?: boolean;
    /**
     * 
     * @type {boolean}
     * @memberof UserPrincipal
     */
    enabled?: boolean;
}
/**
 * @type UserPrincipalUserMeta
 * 
 * @export
 */
export type UserPrincipalUserMeta = Restaurant | UserMeta;
