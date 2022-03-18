import JSONAPISerializer from '@ember-data/serializer/json-api';

export default class ApplicationSerializer extends JSONAPISerializer {
  normalizeResponse(store, primaryModelClass, payload, id, requestType) {
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

      delete song.text;
      delete song.title;
      delete song.phases;
    });

    return super.normalizeResponse(...arguments);
  }
}
