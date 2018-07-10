//  Created by react-native-create-bridge

import React, { Component } from 'react'
import { requireNativeComponent } from 'react-native'

const AliyunPlay = requireNativeComponent('AliyunPlay', AliyunPlayView)

export default class AliyunPlayView extends Component {
  render () {
    return <AliyunPlay {...this.props} onEventCallback={e => console.log('onEventCallback', e)}/>
  }
}

// AliyunPlayView.propTypes = {
//   exampleProp: React.PropTypes.any
// }
