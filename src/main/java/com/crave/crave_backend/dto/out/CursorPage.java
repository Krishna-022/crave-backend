package com.crave.crave_backend.dto.out;

import java.util.List;

public class CursorPage<T> {
	
	private List<T> page;
	
	private Boolean hasMore;
	
	private Long nextCursor;

	public CursorPage(List<T> page, Boolean hasNext, Long nextCursor) {
		this.page = page;
		this.hasMore = hasNext;
		this.nextCursor = nextCursor;
	}

	public List<T> getPage() {
		return page;
	}
	
	public void setPage(List<T> page) {
		this.page = page;
	}

	public Boolean getHasNext() {
		return hasMore;
	}

	public void setHasNext(Boolean hasNext) {
		this.hasMore = hasNext;
	}

	public Long getNextCursor() {
		return nextCursor;
	}

	public void setNextCursor(Long nextCursor) {
		this.nextCursor = nextCursor;
	}
}
