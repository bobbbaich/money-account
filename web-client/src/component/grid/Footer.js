import React from 'react';
import {Footer} from "react-materialize";

export default () =>
    <Footer
        links={
            <ul>
                <li><a className="grey-text text-lighten-3" href="#!">Link 1</a></li>
                <li><a className="grey-text text-lighten-3" href="#!">Link 2</a></li>
                <li><a className="grey-text text-lighten-3" href="#!">Link 3</a></li>
            </ul>
        }
        moreLinks={
            <a className="white-text text-lighten-4 right" href="#!">More Links</a>
        }>
        <h5 className="white-text">Money Account</h5>
        <p className="grey-text text-lighten-3">.</p>
    </Footer>;

