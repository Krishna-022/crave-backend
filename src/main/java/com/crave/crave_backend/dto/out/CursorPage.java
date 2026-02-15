package com.crave.crave_backend.dto.out;

import java.util.List;

public class CursorPage<T> {
	
	private List<T> page;
	
	private Boolean hasMore;
	
	private Long nextCursor;

	public CursorPage(List<T> page, Boolean hasMore, Long nextCursor) {
		this.page = page;
		this.hasMore = hasMore;
		this.nextCursor = nextCursor;
	}

	public List<T> getPage() {
		return page;
	}
	
	public void setPage(List<T> page) {
		this.page = page;
	}

	public Boolean getHasMore() {
		return hasMore;
	}

	public void setHasMore(Boolean hasMore) {
		this.hasMore = hasMore;
	}

	public Long getNextCursor() {
		return nextCursor;
	}

	public void setNextCursor(Long nextCursor) {
		this.nextCursor = nextCursor;
	}
}
