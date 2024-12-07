/* tslint:disable */
/* eslint-disable */
/**
 * OpenAPI definition
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: v0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import type { Restaurant } from './Restaurant';
import {
    instanceOfRestaurant,
    RestaurantFromJSON,
    RestaurantFromJSONTyped,
    RestaurantToJSON,
} from './Restaurant';
import type { UserMeta } from './UserMeta';
import {
    instanceOfUserMeta,
    UserMetaFromJSON,
    UserMetaFromJSONTyped,
    UserMetaToJSON,
} from './UserMeta';

/**
 * @type UserPrincipalUserMeta
 * 
 * @export
 */
export type UserPrincipalUserMeta = Restaurant | UserMeta;

export function UserPrincipalUserMetaFromJSON(json: any): UserPrincipalUserMeta {
    return UserPrincipalUserMetaFromJSONTyped(json, false);
}

export function UserPrincipalUserMetaFromJSONTyped(json: any, ignoreDiscriminator: boolean): UserPrincipalUserMeta {
    if (json == null) {
        return json;
    }
    if (instanceOfRestaurant(json)) {
        return RestaurantFromJSONTyped(json, true);
    }
    if (instanceOfUserMeta(json)) {
        return UserMetaFromJSONTyped(json, true);
    }

    return {} as any;
}

export function UserPrincipalUserMetaToJSON(json: any): any {
    return UserPrincipalUserMetaToJSONTyped(json, false);
}

export function UserPrincipalUserMetaToJSONTyped(value?: UserPrincipalUserMeta | null, ignoreDiscriminator: boolean = false): any {
    if (value == null) {
        return value;
    }

    if (instanceOfRestaurant(value)) {
        return RestaurantToJSON(value as Restaurant);
    }
    if (instanceOfUserMeta(value)) {
        return UserMetaToJSON(value as UserMeta);
    }

    return {};
}

