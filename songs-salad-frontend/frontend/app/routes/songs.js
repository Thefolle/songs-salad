import Route from '@ember/routing/route';
import { inject as service } from '@ember/service';
import { tracked } from '@glimmer/tracking';
import { action } from '@ember/object';

export default class SongsRoute extends Route {
  @service store;
  @service router;

  //  enable lifecycle hooks to trigger re-render
  queryParams = {
    searchInText: {
      refreshModel: true
    },
    searchInTitle: {
      refreshModel: true
    },
    searchPhase: {
      refreshModel: true
    }
  };

  normalizeQueryParam(value) {
    return value !== '' ? value : null;
  }

  @action
  setSearchText(event) {
    let normalizedSearchInText = this.normalizeQueryParam(event.target.value);
    this.router.transitionTo({
      queryParams: { searchInText: normalizedSearchInText }
    });
  }

  @action
  setSearchTitle(event) {
    let normalizedSearchInTitle = this.normalizeQueryParam(event.target.value);
    this.router.transitionTo({
      queryParams: { searchInTitle: normalizedSearchInTitle }
    });
  }

  @action
  setSearchPhase(event) {
    let normalizedSearchPhase = this.normalizeQueryParam(event.target.value);
    this.router.transitionTo({
      queryParams: { searchPhase: normalizedSearchPhase }
    });
  }

  async model(params) {
    let songs = await this.store.query('song', params);
    let phases = ['Enter', 'Glory', 'Psalm', 'Alleluia', 'Offertory', 'Saint', 'Communion', 'End', 'Baptism']

    return {
      songs: songs,
      setSearchText: this.setSearchText,
      setSearchTitle: this.setSearchTitle,
      setSearchPhase: this.setSearchPhase,
      phases
    };
  }
}
