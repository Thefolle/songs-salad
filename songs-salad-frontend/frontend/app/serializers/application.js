import JSONAPISerializer from '@ember-data/serializer/json-api';
import { camelize } from '@ember/string';

export default class ApplicationSerializer extends JSONAPISerializer {
  normalizeQueryResponse(store, primaryModelClass, payload, id, requestType) {
    payload.data = payload.content;
    delete payload.content;

    let songs = payload.data;
    songs.forEach((song) => {
      song.attributes = {
        text: song.text,
        title: song.title,
        phases: song.phases,
      };
      song.type = 'song';

      if (song.sheet) {
        song.relationships = {
          sheet: {
            data: {
              id: song.sheet.id,
              type: 'sheet',
            },
          },
        };
      }

      delete song.text;
      delete song.title;
      delete song.phases;
      delete song.sheet;
    });

    return super.normalizeQueryResponse(...arguments);
  }

  normalizeFindRecordResponse(
    store,
    primaryModelClass,
    payload,
    id,
    requestType
  ) {
    let object = {
      id: payload.id,
      type: primaryModelClass.modelName,
      attributes: {},
    };

    for (let field in payload) {
      if (field !== 'id') {
        object.attributes[field] = payload[field];
      }
    }

    payload.data = object;

    return super.normalizeFindRecordResponse(...arguments);
  }

  /* Camelize an eventual dasherized key, which is Ember's default behavior */
  keyForAttribute(key) {
    return camelize(key);
  }
}
