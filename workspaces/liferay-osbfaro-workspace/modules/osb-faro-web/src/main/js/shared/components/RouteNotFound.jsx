import React from 'react';
import {Redirect} from 'react-router-dom';

/**
 * Component for triggering a redirect to the same page
 * with some extra state for App to decide whether to render a 404 or children.
 */
export default () => <Redirect to={{state: {notFoundError: true}}} />;
