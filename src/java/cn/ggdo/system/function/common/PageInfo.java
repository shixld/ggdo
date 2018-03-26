package cn.ggdo.system.function.common;

import java.io.Serializable;

/**
 * @author steve
 * @see
 * @see
 */
public class PageInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private int currPage = 1;

	private int totalSize = -1;

	private int totalPage = 0;

	private int pageSize = 10;

	// add page
	private long totalRowNum = 0;// total row

	private int currentPageIndex = 1;// current page

	private int totalPageNum = 1;// total page

	private int beginPageIndex = 1;// start page

	private int endPageIndex = 1;// end page

	private boolean hasPrev = false;// Prev page

	private boolean hasNext = false;// next page

	private long firstResult = 0;//

	private long lastResult = 0;//

	public static final int DEFAULT_PAGE_SIZE = 20;// default

	public static final int DEFAULT_PAGE_INDEX_NUM = 5;//

	private int pageIndexNum = DEFAULT_PAGE_INDEX_NUM;// page

	public void turnToPage(int pageIndex) {
		if (pageIndex < 1)
			currentPageIndex = 1;
		else if (pageIndex > totalPageNum)
			currentPageIndex = totalPageNum;
		else
			currentPageIndex = pageIndex;

		hasPrev = (currentPageIndex != 1);
		hasNext = (currentPageIndex != totalPageNum);

		beginPageIndex = Math.max(1, currentPageIndex - pageIndexNum / 2);
		endPageIndex = Math.min(currentPageIndex + (pageIndexNum - pageIndexNum / 2 - 1), totalPageNum);

		firstResult = (currentPageIndex - 1) * pageSize + 1;
		lastResult = Math.min(currentPageIndex * pageSize, totalRowNum);

	}

	public void resetTotalPageNum() {
		if (totalRowNum == 0)
			totalPageNum = 1;
		else {
			totalPageNum = (int) ((totalRowNum + pageSize - 1) / pageSize);
		}
		turnToPage(1);
	}

	public void search(long count) {
		totalRowNum = count;
		resetTotalPageNum();
	}

	public long getTotalRowNum() {
		return totalRowNum;
	}

	public void setTotalRowNum(long totalRowNum) {
		this.totalRowNum = totalRowNum;
	}

	public int getCurrentPageIndex() {
		return currentPageIndex;
	}

	public void setCurrentPageIndex(int currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}

	public int getTotalPageNum() {
		return totalPageNum;
	}

	public void setTotalPageNum(int totalPageNum) {
		this.totalPageNum = totalPageNum;
	}

	public int getBeginPageIndex() {
		return beginPageIndex;
	}

	public void setBeginPageIndex(int beginPageIndex) {
		this.beginPageIndex = beginPageIndex;
	}

	public int getEndPageIndex() {
		return endPageIndex;
	}

	public void setEndPageIndex(int endPageIndex) {
		this.endPageIndex = endPageIndex;
	}

	public boolean isHasPrev() {
		return hasPrev;
	}

	public void setHasPrev(boolean hasPrev) {
		this.hasPrev = hasPrev;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public long getFirstResult() {
		return firstResult;
	}

	public void setFirstResult(long firstResult) {
		this.firstResult = firstResult;
	}

	public long getLastResult() {
		return lastResult;
	}

	public void setLastResult(long lastResult) {
		this.lastResult = lastResult;
	}

	public int getPageIndexNum() {
		return pageIndexNum;
	}

	public void setPageIndexNum(int pageIndexNum) {
		this.pageIndexNum = pageIndexNum;
	}

	public void changePageSize(int pageSize) {
		this.pageSize = pageSize;
		resetTotalPageNum();
	}

	public PageInfo() {
	}

	public PageInfo(int currPage, int totalSize, int totalPage, int pageSize) {
		this.currPage = currPage;
		this.totalPage = totalPage;
		this.totalSize = totalSize;
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStartRecord() {
		int tempStartRecord = (this.getCurrPage() - 1) * this.getPageSize();
		if (tempStartRecord >= this.getTotalSize()) {
			this.currPage = 1;
			reset();
		}
		return (this.getCurrPage() - 1) * this.getPageSize();
	}

	public int getEndRecord() {
		int endRecord = this.getCurrPage() * this.getPageSize();
		if (endRecord > this.getTotalSize()) {
			endRecord = this.getTotalSize();
		}
		return endRecord;
	}

	public int getMaxResult() {
		return this.getEndRecord() - this.getStartRecord();

	}

	public void init(int totalSize) {
		this.setTotalSize(totalSize);
		reset();
	}

	public void reset() {
		int totalPage;
		totalPage = totalSize / this.getPageSize();
		if ((totalSize % this.getPageSize()) != 0) {
			totalPage++;
		}
		this.setTotalPage(totalPage);
	}

}
