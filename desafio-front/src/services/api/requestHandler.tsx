import { URL_API } from "./apiServer";


export async function fetchData(path: string, body: any, method: string) {

  var headers = {
      'Content-Type': 'application/json',
      'ngrok-skip-browser-warning': '69420',
      'Authorization': '',
    };

  var options = body
    ? {
      headers,
      method: method,
      body: JSON.stringify(body),
    }
    : {
      headers,
      method: method,
    };

  const response = await fetch(URL_API + path, options);
  return response;
}
