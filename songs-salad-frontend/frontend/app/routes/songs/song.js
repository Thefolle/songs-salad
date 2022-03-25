import Route from '@ember/routing/route';
import { inject as service } from '@ember/service';

export default class SongsSongRoute extends Route {
  @service store;

  async model(params) {
    let song = await this.store.findRecord('song', params.song_id);
    console.log(song);

    return song;
  }
}
