// Enhanced Theme Management System
class ThemeManager {
    constructor() {
        this.themeToggle = null;
        this.themeIcon = null;
        this.isInitialized = false;

        console.log('[Theme] ThemeManager instance created');
    }

    // Initialize theme management
    init() {
        console.log('[Theme] Initializing theme management...');

        try {
            this.themeToggle = document.getElementById('theme-toggle');
            this.themeIcon = document.getElementById('theme-icon');

            console.log(`[Theme] Elements found - Toggle: ${this.themeToggle ? 'Yes' : 'No'}, Icon: ${this.themeIcon ? 'Yes' : 'No'}`);

            if (!this.themeToggle) {
                throw new Error('Theme toggle element not found');
            }

            // Get and apply initial theme
            const currentTheme = this.getCurrentTheme();
            this.applyTheme(currentTheme);

            // Set up event listener
            this.setupEventListeners();

            // Enable transitions after page load
            this.enableTransitions();

            this.isInitialized = true;
            console.log('[Theme] Initialization complete successfully');

        } catch (error) {
            console.error('[Theme] ERROR during initialization:', error);
            this.handleInitializationError(error);
        }
    }

    // Get current theme from localStorage or system preference
    getCurrentTheme() {
        const savedTheme = localStorage.getItem('theme');
        const systemPrefersDark = window.matchMedia('(prefers-color-scheme: dark)').matches;

        console.log(`[Theme] Saved theme from localStorage: ${savedTheme || 'None'}`);
        console.log(`[Theme] System prefers dark: ${systemPrefersDark}`);

        // Use saved theme if available, otherwise use system preference
        const theme = savedTheme || (systemPrefersDark ? 'dark' : 'light');
        console.log(`[Theme] Determined current theme: ${theme}`);

        return theme;
    }

    // Apply theme to the document
    applyTheme(theme) {
        console.log(`[Theme] Applying theme: ${theme}`);

        // Prevent flash of unstyled content by setting theme immediately
        document.documentElement.setAttribute('data-theme', theme);
        console.log(`[Theme] data-theme attribute set to: ${theme}`);

        // Update theme icon with better accessibility
        this.updateThemeIcon(theme);

        // Persist theme preference
        this.persistTheme(theme);

        // Dispatch custom event for other components to react to theme changes
        this.dispatchThemeChangeEvent(theme);
    }

    // Update theme toggle icon
    updateThemeIcon(theme) {
        if (!this.themeIcon) return;

        // Use Font Awesome icons instead of emojis for better consistency
        const iconClass = theme === 'dark' ? 'bi bi-sun' : 'bi bi-moon';

        // Clear existing content and set new icon
        this.themeIcon.innerHTML = '';
        const iconElement = document.createElement('i');
        iconElement.className = iconClass;
        this.themeIcon.appendChild(iconElement);

        // Update accessibility attributes
        this.themeToggle.setAttribute('aria-label',
            theme === 'dark' ? 'Mudar para tema claro' : 'Mudar para tema escuro');

        console.log(`[Theme] Theme icon updated to: ${iconClass}`);
    }

    // Persist theme preference
    persistTheme(theme) {
        try {
            // Save to localStorage
            localStorage.setItem('theme', theme);
            console.log(`[Theme] Saved theme to localStorage: ${theme}`);

            // Set cookie for server-side persistence (optional)
            this.setThemeCookie(theme);

        } catch (error) {
            console.warn('[Theme] Could not persist theme preference:', error);
        }
    }

    // Set theme cookie for server-side usage
    setThemeCookie(theme) {
        const cookieValue = `theme=${theme}; path=/; max-age=${60 * 60 * 24 * 365}; SameSite=Lax`;
        document.cookie = cookieValue;
        console.log(`[Theme] Cookie set for theme: ${theme}`);
    }

    // Toggle between themes
    toggleTheme() {
        if (!this.isInitialized) {
            console.warn('[Theme] Theme manager not initialized, cannot toggle theme');
            return;
        }

        console.log('[Theme] Theme toggle clicked');

        const currentTheme = document.documentElement.getAttribute('data-theme') || 'light';
        console.log(`[Theme] Current theme before toggle: ${currentTheme}`);

        const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
        console.log(`[Theme] New theme after toggle: ${newTheme}`);

        this.applyTheme(newTheme);

        // Add visual feedback
        this.provideToggleFeedback();
    }

    // Provide visual feedback for theme toggle
    provideToggleFeedback() {
        if (this.themeToggle) {
            // Add temporary animation class
            this.themeToggle.classList.add('theme-toggling');

            setTimeout(() => {
                this.themeToggle.classList.remove('theme-toggling');
            }, 300);
        }
    }

    // Set up all event listeners
    setupEventListeners() {
        // Theme toggle click
        this.themeToggle.addEventListener('click', () => this.toggleTheme());

        // Listen for system theme changes
        this.setupSystemThemeListener();

        // Listen for storage changes (other tabs)
        this.setupStorageListener();

        console.log('[Theme] All event listeners set up');
    }

    // Listen for system theme changes
    setupSystemThemeListener() {
        const mediaQuery = window.matchMedia('(prefers-color-scheme: dark)');

        const handleSystemThemeChange = (e) => {
            // Only follow system theme if no user preference is saved
            if (!localStorage.getItem('theme')) {
                console.log('[Theme] System theme changed, updating to match');
                const newTheme = e.matches ? 'dark' : 'light';
                this.applyTheme(newTheme);
            }
        };

        // Modern browsers
        if (mediaQuery.addEventListener) {
            mediaQuery.addEventListener('change', handleSystemThemeChange);
        }
        // Legacy browsers
        else if (mediaQuery.addListener) {
            mediaQuery.addListener(handleSystemThemeChange);
        }
    }

    // Listen for storage changes (other tabs)
    setupStorageListener() {
        const handleStorageChange = (e) => {
            if (e.key === 'theme' && e.newValue) {
                console.log('[Theme] Theme changed in another tab, updating');
                this.applyTheme(e.newValue);
            }
        };

        window.addEventListener('storage', handleStorageChange);
    }

    // Enable CSS transitions after page load
    enableTransitions() {
        // Wait for next frame to ensure DOM is ready
        requestAnimationFrame(() => {
            document.body.classList.add('loaded');
            console.log('[Theme] Added "loaded" class to body');
        });
    }

    // Dispatch custom event for theme changes
    dispatchThemeChangeEvent(theme) {
        const event = new CustomEvent('themeChanged', {
            detail: { theme }
        });
        document.dispatchEvent(event);
        console.log(`[Theme] Dispatched themeChanged event: ${theme}`);
    }

    // Handle initialization errors gracefully
    handleInitializationError(error) {
        // Apply default theme even if initialization fails
        document.documentElement.setAttribute('data-theme', 'light');
        console.warn('[Theme] Applied default theme due to initialization error');

        // You could also show a user-friendly message if needed
        // this.showErrorMessage('Theme switching is temporarily unavailable');
    }

    // Optional: Show error message to user
    showErrorMessage(message) {
        const errorDiv = document.createElement('div');
        errorDiv.style.cssText = `
            position: fixed;
            top: 10px;
            right: 10px;
            background: #f8d7da;
            color: #721c24;
            padding: 10px;
            border-radius: 4px;
            z-index: 10000;
            font-size: 14px;
        `;
        errorDiv.textContent = message;
        document.body.appendChild(errorDiv);

        setTimeout(() => {
            if (errorDiv.parentNode) {
                errorDiv.parentNode.removeChild(errorDiv);
            }
        }, 5000);
    }

    // Public method to get current theme
    getTheme() {
        return document.documentElement.getAttribute('data-theme') || 'light';
    }

    // Public method to set theme programmatically
    setTheme(theme) {
        if (theme !== 'light' && theme !== 'dark') {
            console.warn('[Theme] Invalid theme specified:', theme);
            return;
        }
        this.applyTheme(theme);
    }
}

// Create global theme manager instance
window.themeManager = new ThemeManager();

console.log('[Theme] Script loaded, waiting for DOM...');

// Initialize when DOM is ready
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', () => {
        console.log('[Theme] DOM fully loaded, initializing theme manager...');
        window.themeManager.init();
    });
} else {
    // DOM already loaded
    console.log('[Theme] DOM already loaded, initializing theme manager immediately...');
    window.themeManager.init();
}

// Add CSS for toggle animation
const style = document.createElement('style');
style.textContent = `
    .theme-toggling {
        transform: scale(0.9);
        transition: transform 0.2s ease;
    }

    #theme-toggle {
        transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
    }

    #theme-toggle:hover {
        transform: scale(1.1);
    }

    /* Smooth transitions for theme changes */
    * {
        transition: background-color 0.3s ease, color 0.3s ease, border-color 0.3s ease !important;
    }
`;
document.head.appendChild(style);