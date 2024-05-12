import {NextResponse} from "next/server";
import {TokenPair} from "../../_core/_models/token-pair";
import {
  tokenAuthenticationStrategy
} from "../../authentication/_services/token-authentication-strategy";

type Target = string | []

/**
 *
 */
type FeedbackAnswer<T> = NextResponse<T>;

/**
 *
 */
class HttpBridge {

  /**
   *
   */
  public getTarget(): string {
    return "http://localhost:8080"
  }

  /**
   *
   */
  public getTargetWithEndpoint(
    path: string
  ): string {
    if (!path) {
      throw new Error("");
    }

    let target = this.getTarget();
    let endpoint = path;


    return `${target}/${endpoint}`;
  }

  public handleResponseError(
    response: Response
  ): void {
    if (0 === response.status) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error("An error occurred:", response.statusText);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${response.status}, body was: `,
        response.json()
      );
    }
  }

  /**
   *
   */
  public async handleResponse<T>(
    response: Response
  ): Promise<T> {
    if (response.ok) {
      return await response.json() as T;
    }

    this.handleResponseError(response);
  }
}

/**
 *
 */
const httpBridge = new HttpBridge();

/**
 *
 */
const defaultHeaders = {
  "Accept": "*/*",
  "Accept-Language": "en-US",
  "Content-Type": "application/json"
}

/**
 *
 */
const withHeaders = (): HeadersInit => {
  //
  if (!tokenAuthenticationStrategy.isLogged()) {
    return defaultHeaders;
  }

  const tokenPair: TokenPair = tokenAuthenticationStrategy.getTokePair();
  return {
    ...defaultHeaders,
    "Authorization": `Bearer ${tokenPair.accessToken}`,
  }
}

/**
 *
 */
const get = async <T, >(
  path: string
): Promise<T> => {
  const target = httpBridge.getTargetWithEndpoint(path);


  return await httpBridge.handleResponse(
    await fetch(target, {
      method: "GET",
      headers: withHeaders()
    })
  );
};

/**
 *
 */
const post = async <T, >(
  path: string,
  body: any | null = {}
): Promise<T> => {
  const target = httpBridge.getTargetWithEndpoint(path);

  return await httpBridge.handleResponse(
    await fetch(target, {
      method: "POST",
      body: JSON.stringify(body),
      headers: withHeaders()
    })
  );
}

export {
  get, post
}