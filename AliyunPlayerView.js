//  Created by react-native-create-bridge

import React, {Component} from 'react'
import {requireNativeComponent, NativeModules} from 'react-native'

const AliyunPlayer = requireNativeComponent('AliyunPlay', AliyunPlayView)
const {AliyunPlayManager} = NativeModules

export default class AliyunPlayView extends Component {
    /**
     *  功能：停止播放视频
     */
    stop = () => {
        AliyunPlayManager.stop()
    }

    /**
     *  功能：暂停播放视频
     *  备注：在start播放视频之后可以调用pause进行暂停。
     */
    pause = () => {
        AliyunPlayManager.pause()
    }

    /**
     *   功能：恢复播放视频
     *   备注：在pause暂停视频之后可以调用resume进行播放。
     */
    resume = () => {
        AliyunPlayManager.resume()
    }

    /**
     *  功能：跳转到指定位置进行播放，单位为秒
     * @param time
     */
    seekToTime = (time) => {
        AliyunPlayManager.seekToTime(time)
    }

    render() {
        return <AliyunPlayer ref={ref => this.aliyunPlay = ref}{...this.props} />
    }
}

// AliyunPlayView.propTypes = {
//   exampleProp: React.PropTypes.any
// }
