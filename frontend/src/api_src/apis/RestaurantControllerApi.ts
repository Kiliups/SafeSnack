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
  PageRestaurant,
  Restaurant,
  RestaurantContainer,
} from '../models/index';

export interface AddRestaurantRequest {
    restaurant: Restaurant;
}

export interface DeleteRestaurantRequest {
    id: number;
}

export interface GetRestaurantByIdRequest {
    id: number;
}

export interface SearchRestaurantRequest {
    keyword: string;
    page?: number;
    size?: number;
}

export interface UpdateRestaurantRequest {
    restaurant: Restaurant;
}

/**
 * 
 */
export class RestaurantControllerApi extends runtime.BaseAPI {

    /**
     */
    async addRestaurantRaw(requestParameters: AddRestaurantRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<Restaurant>> {
        if (requestParameters['restaurant'] == null) {
            throw new runtime.RequiredError(
                'restaurant',
                'Required parameter "restaurant" was null or undefined when calling addRestaurant().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/restaurant`,
            method: 'POST',
            headers: headerParameters,
            query: queryParameters,
            body: requestParameters['restaurant'],
        }, initOverrides);

        return new runtime.JSONApiResponse(response);
    }

    /**
     */
    async addRestaurant(requestParameters: AddRestaurantRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<Restaurant> {
        const response = await this.addRestaurantRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     */
    async deleteRestaurantRaw(requestParameters: DeleteRestaurantRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<string>> {
        if (requestParameters['id'] == null) {
            throw new runtime.RequiredError(
                'id',
                'Required parameter "id" was null or undefined when calling deleteRestaurant().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/restaurant/{id}`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters['id']))),
            method: 'DELETE',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        if (this.isJsonMime(response.headers.get('content-type'))) {
            return new runtime.JSONApiResponse<string>(response);
        } else {
            return new runtime.TextApiResponse(response) as any;
        }
    }

    /**
     */
    async deleteRestaurant(requestParameters: DeleteRestaurantRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<string> {
        const response = await this.deleteRestaurantRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     */
    async getRestaurantByIdRaw(requestParameters: GetRestaurantByIdRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<RestaurantContainer>> {
        if (requestParameters['id'] == null) {
            throw new runtime.RequiredError(
                'id',
                'Required parameter "id" was null or undefined when calling getRestaurantById().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/restaurant/{id}`.replace(`{${"id"}}`, encodeURIComponent(String(requestParameters['id']))),
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response);
    }

    /**
     */
    async getRestaurantById(requestParameters: GetRestaurantByIdRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<RestaurantContainer> {
        const response = await this.getRestaurantByIdRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     */
    async searchRestaurantRaw(requestParameters: SearchRestaurantRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<PageRestaurant>> {
        if (requestParameters['keyword'] == null) {
            throw new runtime.RequiredError(
                'keyword',
                'Required parameter "keyword" was null or undefined when calling searchRestaurant().'
            );
        }

        const queryParameters: any = {};

        if (requestParameters['keyword'] != null) {
            queryParameters['keyword'] = requestParameters['keyword'];
        }

        if (requestParameters['page'] != null) {
            queryParameters['page'] = requestParameters['page'];
        }

        if (requestParameters['size'] != null) {
            queryParameters['size'] = requestParameters['size'];
        }

        const headerParameters: runtime.HTTPHeaders = {};

        const response = await this.request({
            path: `/searchRestaurant`,
            method: 'GET',
            headers: headerParameters,
            query: queryParameters,
        }, initOverrides);

        return new runtime.JSONApiResponse(response);
    }

    /**
     */
    async searchRestaurant(requestParameters: SearchRestaurantRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<PageRestaurant> {
        const response = await this.searchRestaurantRaw(requestParameters, initOverrides);
        return await response.value();
    }

    /**
     */
    async updateRestaurantRaw(requestParameters: UpdateRestaurantRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<runtime.ApiResponse<Restaurant>> {
        if (requestParameters['restaurant'] == null) {
            throw new runtime.RequiredError(
                'restaurant',
                'Required parameter "restaurant" was null or undefined when calling updateRestaurant().'
            );
        }

        const queryParameters: any = {};

        const headerParameters: runtime.HTTPHeaders = {};

        headerParameters['Content-Type'] = 'application/json';

        const response = await this.request({
            path: `/restaurant`,
            method: 'PUT',
            headers: headerParameters,
            query: queryParameters,
            body: requestParameters['restaurant'],
        }, initOverrides);

        return new runtime.JSONApiResponse(response);
    }

    /**
     */
    async updateRestaurant(requestParameters: UpdateRestaurantRequest, initOverrides?: RequestInit | runtime.InitOverrideFunction): Promise<Restaurant> {
        const response = await this.updateRestaurantRaw(requestParameters, initOverrides);
        return await response.value();
    }

}