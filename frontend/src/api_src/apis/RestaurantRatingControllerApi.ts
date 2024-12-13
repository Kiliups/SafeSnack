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


import * as runtime from '../runtime';
import type {
  RestaurantRating,
} from '../models/index';
import {
    RestaurantRatingFromJSON,
    RestaurantRatingToJSON,
} from '../models/index';

/**
 * 
 */
export class RestaurantRatingControllerApi extends runtime.BaseAPI {

    /**
     */
    async getRestaurantRatingRaw(initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<Array<RestaurantRating>>> {
        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/restaurantRating`,
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response, (jsonValue) => jsonValue.map(RestaurantRatingFromJSON));
    }

    /**
     */
    async getRestaurantRating(initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<Array<RestaurantRating>> {
        const response = await this.getRestaurantRatingRaw(initOverrides);
        return await response.value();
    }

}
