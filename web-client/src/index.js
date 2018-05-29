import React from 'react';
import ReactDOM from 'react-dom';
import registerServiceWorker from './action/registerServiceWorker';
import App from "./component/grid/App";

ReactDOM.render(<App/>, document.getElementById('root'));

registerServiceWorker();