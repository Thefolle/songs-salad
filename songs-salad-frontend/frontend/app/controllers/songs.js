import Controller from '@ember/controller';
import { tracked } from '@glimmer/tracking';

export default class SongsController extends Controller {
  queryParams = ['searchInText', 'searchInTitle', 'searchPhase'];

  @tracked searchInText = null;
  @tracked searchInTitle = null;
  @tracked searchPhase = null;
}
