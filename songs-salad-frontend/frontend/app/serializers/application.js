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
        body: song.body.map(fragment => { return {
          id: fragment.id,
          type: 'fragment',
          attributes: {
            text: fragment.text,
            position: fragment.position,
            isChorus: fragment.isChorus
          }
        }}).sort((fragment1, fragment2) => fragment1.attributes.position - fragment2.attributes.position)
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

/* Must first implement /songs/{song_id} on the frontend */
/*      if (song.body) {
        if (!song.relationships) song.relationships = {};
        song.relationships.body = {
          data: song.body.map(body => { return {
                          id: body.id,
                          type: 'fragment'
                        }})
        }



        payload.included = song.body.map(body => { return {
                                  id: body.id,
                                  type: 'fragment',
                                  attributes: {
                                    text: body.text,
                                    position: body.position,
                                    isChorus: body.isChorus
                                  }
                                }})
      }*/

      delete song.text
      delete song.title
      delete song.phases
      delete song.sheet
      delete song.body
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
