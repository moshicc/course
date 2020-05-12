Confirm = {
    //这个组件只有一个方法show(),这个方法，当点击成功的时候会触发另外一个方法，
    // 比如确认的时候会执行删除，这个删除的动作是外部定义的，所以这里用个回调函数
    //简单理解回调函数，将一个函数以参数的 形式来传递到另一个函数中去执行
    show :function (message,callback) {
        Swal.fire({
            title: '确认？',
            text: message,
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '确认!'
        }).then((result) => {
            //这段的意思是如果点击确认 ，就执行外部的方法
            if (result.value){
                //如果这个callback有值，就调用一下这个callback
                if (callback){
                    callback();
                }
            }
        })

    }
};