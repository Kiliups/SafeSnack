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

import { mapValues } from '../runtime';
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
 * Check if a given object implements the PasswordResetContainer interface.
 */
export function instanceOfPasswordResetContainer(value: object): value is PasswordResetContainer {
    return true;
}

export function PasswordResetContainerFromJSON(json: any): PasswordResetContainer {
    return PasswordResetContainerFromJSONTyped(json, false);
}

export function PasswordResetContainerFromJSONTyped(json: any, ignoreDiscriminator: boolean): PasswordResetContainer {
    if (json == null) {
        return json;
    }
    return {
        
        'token': json['token'] == null ? undefined : json['token'],
        'newPassword': json['newPassword'] == null ? undefined : json['newPassword'],
    };
}

export function PasswordResetContainerToJSON(json: any): PasswordResetContainer {
    return PasswordResetContainerToJSONTyped(json, false);
}

export function PasswordResetContainerToJSONTyped(value?: PasswordResetContainer | null, ignoreDiscriminator: boolean = false): any {
    if (value == null) {
        return value;
    }

    return {
        
        'token': value['token'],
        'newPassword': value['newPassword'],
    };
}
