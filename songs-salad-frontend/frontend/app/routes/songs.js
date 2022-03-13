import Route from '@ember/routing/route';
import { inject as service } from '@ember/service';
import { tracked } from '@glimmer/tracking';
import { action } from '@ember/object';

export default class SongsRoute extends Route {
  @service store
  @service router

//  enable lifecycle hooks to trigger re-render
  queryParams = {
    searchInText: {
      refreshModel: true
    }
  }

  normalizeQueryParam(value) {
    return value !== "" ? value : null
  }

  @action
  setSearchText(event) {
    let normalizedSearchInText = this.normalizeQueryParam(event.target.value)
    this.router.transitionTo({ queryParams : { searchInText : normalizedSearchInText } })
  }

  async model(params) {

    let songs = await this.store.query('song', params)

    return {
      songs: songs,
      setSearchText: this.setSearchText
    }
  }
}
