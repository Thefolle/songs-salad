import JSONAPIAdapter from '@ember-data/adapter/json-api';

export default class ApplicationAdapter extends JSONAPIAdapter {
  host = 'http://localhost:8081';
  headers = {
    'Content-Type': 'application/vnd.api+json',
  };
}
