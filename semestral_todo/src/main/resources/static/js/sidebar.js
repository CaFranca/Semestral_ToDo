// sidebar.js - Updated
class SidebarManager {
    constructor() {
        this.sidebar = document.getElementById('sidebar');
        this.main = document.getElementById('main');
        this.btnOpen = document.getElementById('sidebarToggleDesktop');
        this.btnOpenMobile = document.getElementById('sidebarToggleMobile');
        this.btnClose = document.getElementById('sidebarClose');
        this.sidebarOverlay = document.getElementById('sidebarOverlay');
        this.resetFilters = document.getElementById('resetFilters');

        this.isOpen = false;
        this.sidebarWidth = '320px';
        this.transitionDuration = 300;

        this.init();
    }

    init() {
        // Set initial state - sidebar closed
        this.setInitialStyles();
        this.addEventListeners();

        console.log('[Sidebar] Sidebar manager initialized - starting closed');
    }

    setInitialStyles() {
        // Ensure sidebar starts closed
        this.sidebar.classList.remove('open');
        this.main.classList.remove('with-sidebar');
        this.sidebarOverlay.classList.remove('active');
        document.body.classList.remove('sidebar-open');

        // Set transitions
        this.sidebar.style.transition = `left ${this.transitionDuration}ms ease, transform ${this.transitionDuration}ms ease`;
        this.main.style.transition = `margin-left ${this.transitionDuration}ms ease, transform ${this.transitionDuration}ms ease`;
    }

    addEventListeners() {
        // Desktop toggle
        if (this.btnOpen) {
            this.btnOpen.addEventListener('click', () => this.toggle());
        }

        // Mobile toggle
        if (this.btnOpenMobile) {
            this.btnOpenMobile.addEventListener('click', () => this.toggle());
        }

        // Close sidebar
        if (this.btnClose) {
            this.btnClose.addEventListener('click', () => this.close());
        }

        // Close sidebar when clicking overlay (mobile)
        if (this.sidebarOverlay) {
            this.sidebarOverlay.addEventListener('click', () => this.close());
        }

        // Close sidebar with Escape key
        document.addEventListener('keydown', (e) => {
            if (e.key === 'Escape' && this.isOpen) {
                this.close();
            }
        });

        // Reset filters
        if (this.resetFilters) {
            this.resetFilters.addEventListener('click', () => this.resetFilterForm());
        }

        // Handle window resize
        window.addEventListener('resize', () => this.handleResponsiveBehavior());
    }

    open() {
        this.sidebar.classList.add('open');
        this.main.classList.add('with-sidebar');
        this.isOpen = true;

        // Show overlay on mobile
        if (window.innerWidth <= 768) {
            this.sidebarOverlay.classList.add('active');
            document.body.classList.add('sidebar-open');
        }

        this.dispatchEvent('sidebarOpened');
        console.log('[Sidebar] Sidebar opened');
    }

    close() {
        this.sidebar.classList.remove('open');
        this.main.classList.remove('with-sidebar');
        this.isOpen = false;

        // Hide overlay
        this.sidebarOverlay.classList.remove('active');
        document.body.classList.remove('sidebar-open');

        this.dispatchEvent('sidebarClosed');
        console.log('[Sidebar] Sidebar closed');
    }

    toggle() {
        if (this.isOpen) {
            this.close();
        } else {
            this.open();
        }
    }

    handleResponsiveBehavior() {
        const isMobile = window.innerWidth <= 768;

        if (isMobile && this.isOpen) {
            // On mobile, ensure overlay is active when sidebar is open
            this.sidebarOverlay.classList.add('active');
            document.body.classList.add('sidebar-open');
        } else if (!isMobile && this.isOpen) {
            // On desktop, remove overlay when sidebar is open
            this.sidebarOverlay.classList.remove('active');
            document.body.classList.remove('sidebar-open');
        }
    }

    resetFilterForm() {
        const baseUrl = window.location.pathname;
                window.location.href = baseUrl;
    }

    dispatchEvent(eventName) {
        const event = new CustomEvent(eventName, {
            detail: { isOpen: this.isOpen }
        });
        document.dispatchEvent(event);
    }

    getState() {
        return {
            isOpen: this.isOpen,
            width: this.sidebarWidth
        };
    }

    destroy() {
        // Clean up event listeners if needed
        console.log('[Sidebar] Sidebar manager destroyed');
    }
}

// Initialize sidebar when DOM is ready
function initSidebar() {
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', () => {
            window.sidebarManager = new SidebarManager();
        });
    } else {
        window.sidebarManager = new SidebarManager();
    }
}

// Auto-initialize
initSidebar();

// Export for module systems
if (typeof module !== 'undefined' && module.exports) {
    module.exports = SidebarManager;
}