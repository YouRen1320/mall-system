package com.youren.mall.common.result;

import java.util.List;

public record PageResult<T>(
        long total,
        long pages,
        long current,
        long size,
        List<T> records
) {
}