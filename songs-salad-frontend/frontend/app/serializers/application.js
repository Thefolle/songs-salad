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
        sheet: song.sheet
      };
      song.type = 'song';

      console.log(song.sheet)

      delete song.text;
      delete song.title;
      delete song.phases;
      delete song.sheet
    });

    return super.normalizeResponse(...arguments);
  }
}
